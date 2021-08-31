package com.jenkins
import net.sf.json.*

def getConsulConfig() {
  return readJSON(text: env.CONSUL_CONFIG)
}

def defaultJobParameters() {
  return [
    booleanParam(name: 'muteSlackNotifications', description: 'Check this parameter to mute slack notifications received at Slackbot')
  ]
}