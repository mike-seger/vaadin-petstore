# Helpers HOWTO

## Generate countries.tsv
```
helpers/countries-json2tsv.sh src/main/resources/db/changelog/data/countries.json \
    src/main/resources/db/changelog/data/002-countries.tsv
```

## Generate valid phone numbers
https://www.mockaroo.com/

### valid phone numbers for selected countries
curl -X POST -d "number=60&twoLettersCode=DE&X-Requested-With=XMLHttpRequest" https://randommer.io/free-valid-bulk-telephones-generator

```
cat src/main/resources/db/changelog/data/002-countries.tsv | \
while IFS=$'\t' read -r id name iso2 iso3 phone_code emoji emojiU; do
    curl -s -X POST -d \
    "number=15&twoLettersCode=$iso2&X-Requested-With=XMLHttpRequest" \
    https://randommer.io/free-valid-bulk-telephones-generator | \
    tr -d '[] \t"'|tr , "\n" |sed -e 's/^\(.*\)/'$name'\t"\1"/'
    echo ""
done
```

## Local Postgres DB
### Configure in local application
config/application.yaml
```
spring:
  datasource:
    url: jdbc:postgresql://localhost/public
    username: postgres
    password: pssword
```
### Start instance
```
docker run -p 5432:5432 --name petstore --rm -e POSTGRES_PASSWORD=pssword -d postgres
```

### Drop tables
```
drop table country cascade;
drop table customer cascade;
drop table databasechangelog cascade;
drop table databasechangeloglock cascade;
drop table pet cascade;
drop table purchase cascade;
drop table species cascade;
```