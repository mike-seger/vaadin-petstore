# Generate valid phone numbers
https://www.mockaroo.com/

## valid phone numbers for selected countries
curl -X POST -d "number=60&twoLettersCode=DE&X-Requested-With=XMLHttpRequest" https://randommer.io/free-valid-bulk-telephones-generator

```
cat countries.txt | while IFS=$'\t' read -r cc c; do 
    curl -s -X POST -d \
    "number=15&twoLettersCode=$cc&X-Requested-With=XMLHttpRequest" \
    https://randommer.io/free-valid-bulk-telephones-generator | \
    tr -d '[] \t"'|tr , "\n" |sed -e 's/^\(.*\)/'$c'\t"\1"/'
    echo ""
done
