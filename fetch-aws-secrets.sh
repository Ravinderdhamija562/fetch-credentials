#!/bin/bash

# Get a list of all secret ARNs in the current region and account
secret_arns=$(aws secretsmanager list-secrets --query 'SecretList[*].ARN' --output text)

# Check if any secrets were found
if [ -z "$secret_arns" ]; then
    echo "No secrets found in AWS Secrets Manager."
    exit 0
fi

echo "All Secret ARNs:"
echo "$secret_arns"

echo "-------------------------------------"

# Loop through each secret ARN
for arn in $secret_arns; do
    echo "Secret: $arn"
    
    # Retrieve the secret value (SecretString) and handle potential errors
    secret_value=$(aws secretsmanager get-secret-value --secret-id "$arn" --query SecretString --output text 2>/dev/null)
    
    # Check if the secret value was retrieved successfully
    if [ $? -eq 0 ]; then
        echo "$secret_value"
    else
        echo "Could not find value for '$arn'. It might be a binary secret or an error occurred."
    fi
    echo "-------------------------------------"
done

