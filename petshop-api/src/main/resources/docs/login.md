# Autenticação

## Login - Admin do Sistema

### Request

```http
POST /api/auth/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cookie: XSRF-TOKEN=7b5c2227-ecf1-4cf5-a547-4f62beab6d0e
Content-Length: 67

{
  "email": "juliane.vmaran@gmail.com",
  "password": "P&etSh0p"
}
```

### Response

```json
{
  "authToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJQZXRTaG9wIiwic3ViIjoianVsaWFuZS52bWFyYW5AZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9TVVBFUl9BRE1JTiJdLCJlbWFpbCI6Imp1bGlhbmUudm1hcmFuQGdtYWlsLmNvbSIsImlhdCI6MTcyOTM0OTIzNiwiZXhwIjoxNzI5MzUyODM2fQ.r532wFNa2z7RpJNCGScHK2_P6JqGa9-RrCFUBvADEGw",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "username": "juliane.vmaran@gmail.com",
  "name": "Admin Sistema",
  "roles": [
    "ROLE_SUPER_ADMIN"
  ]
}
```

## Detalhes do Usuário - Admin Sistema

### Request

```http
GET /api/auth/me HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJQZXRTaG9wIiwic3ViIjoianVsaWFuZS52bWFyYW5AZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9TVVBFUl9BRE1JTiJdLCJlbWFpbCI6Imp1bGlhbmUudm1hcmFuQGdtYWlsLmNvbSIsImlhdCI6MTcyOTM0OTIzNiwiZXhwIjoxNzI5MzUyODM2fQ.r532wFNa2z7RpJNCGScHK2_P6JqGa9-RrCFUBvADEGw
```

### Response

```json
{
    "id": 1,
    "name": "Admin Sistema",
    "cpf": "060.690.439-52",
    "email": "juliane.vmaran@gmail.com",
    "phone": "+5511999999999",
    "enabled": true,
    "roles": [
        {
            "id": 1,
            "name": "SUPER_ADMIN",
            "description": "Administrador do Sistema (Super Usuário)"
        }
    ]
}
```