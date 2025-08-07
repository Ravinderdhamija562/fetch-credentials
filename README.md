# fetch-credentials

Fetch credentials from various backends

## Fetch AWS Secrets

### How to fetch AWS Secrets

```sh
export AWS_PROFILE="<AWS_PROFILE_NAME>" or set AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY
export AWS_REGION="<AWS_REGION>"
./fetch-aws-secrets.sh
```

## Fetch GCP Secrets

### How to fetch GCP Secrets

```sh
export GOOGLE_APPLICATION_CREDENTIALS="<PATH_TO_GCP_CREDENTIALS_JSON>"
./fetch-gcp-gsm-creds.sh
```

## Fetch Github Action secrets

### How to fetch Github Action secrets

Copy the workflow `fetch-github-secret-workflow.yml` to your `.github/workflows/` directory and run it.

## Fetch Jenkins Secrets

### How to fetch Jenkins Secrets

Run the code present in `fetch-jenkins-secrets.groovy` script in jenkins script console.

