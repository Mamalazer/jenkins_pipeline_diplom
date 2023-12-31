#!groovy

def getTelegramBody() {
    return """
            {
                "base": {
                "project": "${env.JOB_BASE_NAME}",
                "environment": "qa.guru",
                "comment": "создатель сборки @Mamalazer",
                "reportLink": "${env.BUILD_URL}",
                "language": "en",
                "allureFolder": "allure-report/",
                "enableChart": true
                },
                "telegram": {
                    "token": "${env.TELEGRAM_TOKEN}",
                    "chat": "-1001729665881",
                    "replyTo": ""
                }
            }"""
}