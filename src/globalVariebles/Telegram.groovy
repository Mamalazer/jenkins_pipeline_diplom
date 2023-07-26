static String telegramMessageBody = """{
                    "base": {
                        "project": "${env.JOB_BASE_NAME}",
                        "environment": "${params.ENVIRONMENT}",
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