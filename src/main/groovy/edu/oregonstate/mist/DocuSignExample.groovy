package edu.oregonstate.mist

import com.docusign.esign.api.*
import com.docusign.esign.client.*
import com.docusign.esign.model.*

import java.util.List

public class DocuSignExample {
    public static void run(def properties) {

        // initialize client for desired environment and add X-DocuSign-Authentication header
        ApiClient apiClient = new ApiClient()
        apiClient.setBasePath(properties.basePath)

        // configure 'X-DocuSign-Authentication' authentication header
        String authHeader = "{\"Username\":\"" +  properties.username + "\",\"Password\":\"" +
                properties.password + "\",\"IntegratorKey\":\"" +  properties.integratorKey + "\"}"
        apiClient.addDefaultHeader("X-DocuSign-Authentication", authHeader)

        Configuration.setDefaultApiClient(apiClient)
        try
        {

            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            // STEP 1: AUTHENTICATE TO RETRIEVE ACCOUNTID & BASEURL         
            /////////////////////////////////////////////////////////////////////////////////////////////////////////

            AuthenticationApi authApi = new AuthenticationApi()

            AuthenticationApi.LoginOptions loginOptions = new AuthenticationApi.LoginOptions()
            loginOptions.setApiPassword("true")
            loginOptions.setIncludeAccountIdGuid("true")

            LoginInformation loginInfo = authApi.login(loginOptions)

            // parse first account ID (user might belong to multiple accounts) and baseUrl
            String accountId = loginInfo.getLoginAccounts().get(0).getAccountId()
            String baseUrl = loginInfo.getLoginAccounts().get(0).getBaseUrl()
            String[] accountDomain = baseUrl.split("/v2")

            // below code required for production, no effect in demo (same domain) 
            apiClient.setBasePath(accountDomain[0])
            Configuration.setDefaultApiClient(apiClient)

            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            // *** STEP 2: CREATE ENVELOPE FROM TEMPLATE       
            /////////////////////////////////////////////////////////////////////////////////////////////////////////

            // create a new envelope to manage the signature request
            EnvelopeDefinition envDef = new EnvelopeDefinition()
            envDef.setEmailSubject("DocuSign Java SDK - Sample Signature Request")

            // assign template information including ID and role(s)
            envDef.setTemplateId(properties.templateId)

            // create a template role with a valid templateId and roleName and assign signer info
            TemplateRole tRole = new TemplateRole()
            tRole.setRoleName(properties.roleName)
            tRole.setName(properties.name)
            tRole.setEmail(properties.email)

            // create a list of template roles and add our newly created role
            java.util.List<TemplateRole> templateRolesList = new ArrayList<TemplateRole>()
            templateRolesList.add(tRole)

            // assign template role(s) to the envelope 
            envDef.setTemplateRoles(templateRolesList)

            // send the envelope by setting |status| to "sent". To save as a draft set to "created"
            envDef.setStatus("sent")

            // instantiate a new EnvelopesApi object
            EnvelopesApi envelopesApi = new EnvelopesApi()

            // call the createEnvelope() API
            EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope(accountId, envDef)
        }
        catch (com.docusign.esign.client.ApiException ex)
        {
            System.out.println("Exception: " + ex)
        }
    }
} 