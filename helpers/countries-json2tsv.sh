#!/bin/bash

json=$1
if [ $# != 2 ] ; then
	echo "Usage: $0  <json file> <output tsv file>"
	exit 1
fi


if [ ! -f "$json" ] ; then
	echo "[$1] is not a json file"
	exit 1
fi

tsv=$2

function string2N() { printf "%d" "0x$(echo $1|tr -d "\n\r"|xxd -pu)"; }
function n2String() { printf "%x" "$1" |xxd -r -p; }

f=".name, .iso2, .iso3, .phone_code, .emoji"
echo "id,$f"|tr -d ". "|tr , "\t" | tee $tsv
jq -c --raw-output '.[] |['"$f"']|@tsv' $json |\
while IFS=$'\t' read name iso2 iso3 phone_code emoji; do
	pc=$(echo $phone_code|tr -d '+-'|sed -e 's/^00//')
	id=$(string2N $iso3)
	printf -- "$id\t$name\t$iso2\t$iso3\t$pc\t$emoji\n"
done | tee -a $tsv
