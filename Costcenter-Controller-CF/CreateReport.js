const report = require('cucumber-html-report');
report.create({
  source:       'reports/e2e/cucumber.json',      // source json
  dest:         'reports/e2e/prod',                   // target directory (will create if not exists)
  name:         'report.html',                 // report file name (will be index.html if not exists)
  title:        'Cucumber Report',             // Title for default template. (default is Cucumber Report)
  component:    'My Component',                // Subtitle for default template. (default is empty)
  screenshots:  'reports/e2e/screenshots',               // Path to the directory of screenshots. Optional.
  maxScreenshots: 10                           // Max number of screenshots to save (default is 1000)
})
.then(console.log)
.catch(console.error);