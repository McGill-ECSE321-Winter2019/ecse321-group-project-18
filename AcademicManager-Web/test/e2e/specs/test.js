// For authoring Nightwatch tests, see
// http://nightwatchjs.org/guide#usage

module.exports = {
  'Homepage Test': function (browser) {
    // automatically uses dev Server port from /config.index.js
    // default: http://localhost:8080
    // see nightwatch.conf.js
    const devServer = browser.globals.devServerURL

    browser
      .url(devServer)
      .waitForElementVisible('#app', 5000)
      .waitForElementVisible('div[id="banner"]', 4000)
      .waitForElementVisible('div[id="sidebar"]', 4000)
      .waitForElementVisible('img[id="logo"]', 4000)
      .end()
  }
}
