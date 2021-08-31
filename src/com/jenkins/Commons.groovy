package com.jenkins

/**
 * Generate a map of all utilities for general use. The use of this method is designed to avoid the need to instantiate any of the included utilities across all pipelines, individually.
 *
 * @return map of instances
 */
def get() {
  return [
      Parameters: new Parameter(),
      Paramutil: new ParameterUtilities(), 
      InputParameterType: InputParameterType
  ]
}

return this

