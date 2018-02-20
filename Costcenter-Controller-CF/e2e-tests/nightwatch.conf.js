const argv = require("yargs").argv;
const seleniumServer = require('selenium-server');
const geckodriver = require("geckodriver");

require('nightwatch-cucumber')({
    cucumberArgs: ['--require', 'timeout.js', '--require', 'features/step_definitions', '--format', 'pretty', '--format', 'json:../s4hana_pipeline/reports/e2e/cucumber.json', 'features']
})

var chromeOptionsArgs = ["--no-sandbox" , "window-size=1400,1000"]
if(argv.headless){
    chromeOptionsArgs.push("--headless")
    chromeOptionsArgs.push("--disable-gpu")
}

const options = {
    output_folder: '../reports/e2e',
    custom_assertions_path: '',
    page_objects_path: 'page_objects',
    live_output: false,
    disable_colors: false,
    globals_path: "external.globals.js",
    selenium: {
        "start_process": true,
        "server_path": seleniumServer.path,
        "log_path": "",
        "port": 4444,
        "cli_args": {
            "webdriver.gecko.driver": geckodriver.path
        }
    },
    test_settings: {
        default: {
            launch_url : argv.lauchurl,
            selenium_port: 4444,
            selenium_host: '127.0.0.1',
            default_path_prefix: "/wd/hub",
            desiredCapabilities: {
                browserName: "firefox",
                javascriptEnabled: true,
                acceptSslCerts: true,
                acceptInsecureCerts: true,
                marionette: true
            },
            globals: {
                abortOnAssertionFailure : true,
                retryAssertionTimeout: 10000,
                waitForConditionTimeout: 10000,
                asyncHookTimeout : 10000
            }
        }
    }
}

module.exports = options;