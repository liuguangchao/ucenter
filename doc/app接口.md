### 用户

### 获取短信验证码
GET /code/authcode/{tel}

url参数

    tel: 手机号码

返回示例

    {
      "code":"0",
      "message":"操作成功！"
    } 

***

### 验证短信验证码

POST /code/authcode

url参数

```
tel: 手机号码
code:验证码
```

返回示例

```
{
  "code":"0",
  "message":"操作成功！"
} 
```

------

### 

### 注册

POST /user/register

参数

    tel: 手机号码
    code: 短信验证码
    password:密码

返回示例

    {
        "code": 0,
        "message": "操作成功！",
        "data": {
            "imei": null,
            "id": 15,
            "username": "18311339042",
            "token": "EF64DA28D1FED0E8361EA2F6A66DCBE9"
        }
    }

***

### 凭短信验证码登录
POST /sso/login

参数

    tel: 手机号码
    code: 短信验证码

返回示例 

    {
        "code": 0,
        "message": "操作成功！",
        "data": {
            "imei": null,
            "id": 15,
            "username": "18311339042",
            "token": "EF64DA28D1FED0E8361EA2F6A66DCBE9"
        }
    }

***

### 密码登录

POST /sso/login

参数

```
tel: 手机号码
password: 密码
```

返回示例 

```
{
    "code": 0,
    "message": "操作成功！",
    "data": {
        "imei": null,
        "id": 15,
        "username": "18311339042",
        "token": "EF64DA28D1FED0E8361EA2F6A66DCBE9"
    }
}
```

------

### 验证token登录

POST /sso/token

参数

```
userId: 用户id
token: token
```

返回示例 

```
{
    "code": 0,
    "message": "操作成功！"
}
```

------

