This is a plugin for bungee (support for other servers later on) that allows RESTful queries to be made

# Checks
![](https://github.com/Fernthedev/ServerRestAPI/workflows/Java%20CI/badge.svg)
[![Build Status](https://dev.azure.com/Fernthedev/ServerRestAPI/_apis/build/status/Fernthedev.ServerRestAPI?branchName=master)](https://dev.azure.com/Fernthedev/ServerRestAPI/_build/latest?definitionId=6&branchName=master)

## Setup
### NGINX
I have an example configuration for setting up for NGINX since this allows for domains, subdomains, locations, better performance and security.
This also allows usage of HTTPS for websites that are running HTTPS since browsers require all requests on HTTPS sites to use HTTPS.

This configuration is based on Let's Encrypt HTTPS setup.
```
server {

    root /var/www/html/site;
    index index.html index.htm index.nginx-debian.html;

    server_name site.com www.site.com;

	location /bungee/ {
      proxy_set_header        Host $host;
      proxy_set_header        X-Real-IP $remote_addr;
      proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;
      proxy_set_header        X-Forwarded-Proto $scheme;
      proxy_pass http://localhost:3000/;     
	}

    location / {
      try_files $uri $uri/ =404;
    }

    listen [::]:443 ssl ipv6only=on; # managed by Certbot
    listen 443 ssl; # managed by Certbot

    ssl_certificate /etc/letsencrypt/live/site.com/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/site.com/privkey.pem; # managed by Certbot

#    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    ssl_ciphers EECDH+CHACHA20:EECDH+AES128:RSA+AES128:EECDH+AES256:RSA+AES256:EECDH+3DES:RSA+3DES:!MD5;

    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot
    proxy_ssl_session_reuse on;
            
}

server {
    if ($host = www.site.com) {
        return 301 https://$host$request_uri;
    } # managed by Certbot


    if ($host = site.com) {
        return 301 https://$host$request_uri;
    } # managed by Certbot


        listen 80;
        listen [::]:80;

        server_name site.com www.site.com;
    return 404; # managed by Certbot
}

```

## Queries

For now, it only runs on bungee and it's only query is a POST request for online server statuses.

### Server Status List

The list of servers is generated on the config based on servers the proxy has registered. You can hide them from the status check in the config
You can also add your own servers as well.

```
http://server:port/api/servers/list/get // Server Status List
```

Result:
```json
{
  "serverMap": {
    "server1": false, // false = offline/true = online
    "server2": false,
    "server3": true,
    "server4": false
  }
}
```
