module.exports = {
  'Student Page Test': function (browser) {
    // automatically uses dev Server port from /config.index.js
    // default: http://localhost:8080
    // see nightwatch.conf.js
    const devServer = browser.globals.devServerURL
    const category = '/#/internships'

    browser
      .url(devServer + category)
      .waitForElementVisible('div[id="banner"]', 4000)
      .waitForElementVisible('img[id="logo"]', 4000)
      .waitForElementVisible('div[id="sidebar"]', 4000)
      .waitForElementVisible('input[name="input-student-id"]', 4000)
      .assert.urlContains(category)
      .end();
  }
}
