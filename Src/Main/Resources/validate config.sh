#!/bin/bash

CONFIG_FILE="Resources/config.properties"

# Check if the configuration file exists
if [ ! -f "$CONFIG_FILE" ]; then
    echo "Error: Configuration file not found at $CONFIG_FILE."
    exit 1
fi

# Read the configuration file
declare -A config
while IFS='=' read -r key value; do
    if [[ ! "$key" =~ ^# ]] && [[ -n "$key" ]]; then
        config["$key"]=$(echo "$value" | xargs)
    fi
done < "$CONFIG_FILE"

# Validate required properties
required_keys=("app.name" "app.version" "server.ip" "server.port" "database.url" "database.username" "database.password" "logging.level" "logging.filepath")

for key in "${required_keys[@]}"; do
    if [ -z "${config[$key]}" ]; then
        echo "Error: Missing required configuration property: $key"
        exit 1
    fi
done

echo "All configuration properties are valid."
