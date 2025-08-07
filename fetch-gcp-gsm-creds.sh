#!/bin/bash
#It fetches the secrets from currently set gcloud profile
for secret in `gcloud secrets list | grep -v NAME | awk '{print $1}'`
do
echo -e "\n\n$secret"
gcloud secrets versions access latest --secret=$secret
done
