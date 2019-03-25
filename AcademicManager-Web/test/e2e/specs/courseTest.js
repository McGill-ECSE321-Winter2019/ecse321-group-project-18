module.exports = {
  'Course Page Test': function (browser) {
    // automatically uses dev Server port from /config.index.js
    // default: http://localhost:8080
    // see nightwatch.conf.js
    const devServer = browser.globals.devServerURL

    browser
      .url(devServer + '/#/courses')
      .waitForElementVisible('div[id="banner"]', 4000)
      .waitForElementVisible('img[id="logo"]', 4000)
      .waitForElementVisible('div[id="sidebar"]', 4000)
      .waitForElementVisible('input[name="input-course-id"]', 4000)
      .waitForElementVisible('input[name="input-term-id"]', 4000)
      .waitForElementVisible('button[name="filter-course-by-id"]', 4000)
      .waitForElementVisible('button[name="filter-course-by-quantity"]', 4000)
      .assert.urlContains('courses')
      .end();
  }
}
