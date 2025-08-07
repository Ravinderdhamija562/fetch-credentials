import java.util.Base64

def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
    com.cloudbees.plugins.credentials.Credentials.class
)

for (c in creds) {
    println("ID: " + c.id)
    println("   Description: " + (c.description ?: "No description"))

    if (c instanceof com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl) {
        println("   Type: Username/Password")
        println("   Username: " + c.username)
        println("   Password: " + c.password.getPlainText())
    } else if (c instanceof org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl) {
        println("   Type: Secret Text")
        println("   Secret: " + c.secret.getPlainText())
    } else if (c instanceof com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey) {
        println("   Type: SSH Key")
        println("   Username: " + c.username)
        println("   Private Key: " + c.privateKey)
    } else if (c instanceof org.jenkinsci.plugins.plaincredentials.impl.FileCredentialsImpl) {
        println("   Type: File")
        println("   File Name: " + c.fileName)
        println("   File Content (decoded): " + new String(c.content.bytes))
    } else if (c instanceof com.cloudbees.jenkins.plugins.awscredentials.AWSCredentialsImpl) {
        println("   Type: AWS Credentials")
        println("   Access Key ID: " + c.accessKey)
        println("   Secret Key: [REDACTED]")
    } else {
        // Check for GoogleRobotPrivateKeyCredentials dynamically
        def googleCredClass = this.class.classLoader.loadClass("com.google.jenkins.plugins.credentials.oauth.GoogleRobotPrivateKeyCredentials")
        if (googleCredClass.isInstance(c)) {
            println("   Type: Google Service Account")
            try {
                // Safely access account ID and JSON key
                def accountId = c.metaClass.respondsTo(c, "getAccountId") ? c.getAccountId() : "No accountId method"
                def jsonKeyBase64 = c.metaClass.respondsTo(c, "getServiceAccountConfig") ? 
                                    c.getServiceAccountConfig()?.getJsonKey() : null
                def jsonKeyDecoded = jsonKeyBase64 ? new String(Base64.getDecoder().decode(jsonKeyBase64)) : "No JSON key"
                println("   Account ID: " + accountId)
                println("   JSON Key (decoded): " + jsonKeyDecoded)
            } catch (Exception e) {
                println("   Error accessing Google credential properties: " + e.message)
            }
        } else {
            println("   Type: Unknown")
            println("   Class: " + c.getClass().getName())
        }
    }
    println("")
}
