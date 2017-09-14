const { client } = require('nightwatch-cucumber')
const { defineSupportCode } = require('cucumber')

defineSupportCode(({ Given, Then, When }) => {
  Given(/^I open the Cost Center home page$/, () => {
    const costcenter = client.page.costcenter()
	console.info (costcenter.url())
    return costcenter
      .navigate()
      .waitForElementVisible('@body')
  })

  Then(/^the title is "(.*?)"$/, (text) => {
    const costcenter = client.page.costcenter()
    return costcenter.assert.visible('@title')
  })

  Then(/^the Create button exists$/, () => {
    const costcenter = client.page.costcenter()
    return costcenter.assert.visible('@create')
  })

})