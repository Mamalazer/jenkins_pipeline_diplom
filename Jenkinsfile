@Library('jenkins_pipeline_diplom') _

pipeline {
    agent any
    
    tools {
        gradle "Gradle 8.2"
        allure "Allure 2.22.0"
    }
    
    options { 
        buildDiscarder(logRotator(numToKeepStr: '5')) 
        timeout (time: 1, unit: 'HOURS')
        timestamps()
    } 
    
    parameters {
        string(name: "ENVIRONMENT", defaultValue: "test", trim: true, description: "Environment")
        string(name: "TAG", trim: true, description: "Тэг тестов для запуска. Если поле не заполнено, то осуществится запуск всех тестов проекта")
        string(name: "THREADS", trim: true, description: "Количество потоков для запуска тестов. Если поле не заполнено, то тесты запускаются в один поток")
        string(name: "REMOTE_URL", defaultValue: "https://user1:1234@selenoid.autotests.cloud", trim: true, description: "Адрес remote сервиса для запуска тестов")
        string(name: "BROWSER_SIZE", defaultValue: "1920x1080", trim: true, description: "Разрешение web браузера")
        choice(name: "BROWSER_NAME", choices: ["chrome", "firefox"], description: "Веб браузер для UI тестов")
        choice(name: "BROWSER_VERSION", choices: ["100.0", "99.0", "98.0", "97.0"], description: "Варианты браузеров и их версий: chrome 100.0 и 99.0, firefox 97.0 и 98.0")
        string(name: "FRONT_URL", defaultValue: "https://www.saucedemo.com", trim: true, description: "URL фронта")
        string(name: "ANDROID_DEVICE", defaultValue: "Google Pixel 3", trim: true, description: "Имя Android устройства")
        string(name: "ANDROID_VERSION", defaultValue: "9.0", trim: true, description: "Версия Android")
    }

    triggers {
        parameterizedCron('''
            H/30 * * * * %TAG=Api;THREADS=2
        ''')
    }
    
    stages {
        stage('Prepare telegram report') { 
            steps {
                script {
                    String body = telegramMessageBody.getTelegramBody()
                    writeFile file: 'src/test/resources/telegram.json', text: "${body}"
                }
            }
        }
        stage('Build') {
            steps {
                git branch: 'master', url: 'https://github.com/Mamalazer/qa_guru_final_project'
                sh """gradle clean \
                test \
                -Denv="${params.ENVIRONMENT}" \
                -Dtag=${params.TAG} \
                -Dthreads=${params.THREADS} \
                -DwebIsRemote=true \
                -DwebRemoteUrl=${params.REMOTE_URL} \
                -DwebBaseUrl=${params.FRONT_URL} \
                -DwebBrowserSize=${params.BROWSER_SIZE} \
                -DwebBrowserName="${params.BROWSER_NAME}" \
                -DwebBrowserVersion="${params.BROWSER_VERSION}" \
                -DmobilePlatform=browserstack \
                -DandroidDevice="${params.ANDROID_DEVICE}" \
                -DandroidVersion="${params.ANDROID_VERSION}" \
                -DbuildName=${env.JOB_BASE_NAME}"""
            }
        }                
    }

    post {
        always {
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
            sh 'java -DconfigFile=src/test/resources/telegram.json -jar src/test/resources/allure-notifications-4.2.1.jar'
        }
    }
}