package com.github.jmchilton.blend4j.galaxy.beans;


import org.codehaus.jackson.annotate.JsonProperty;


/**
 * Created with IntelliJ IDEA.
 * User: mlogghe
 * Date: 4/3/13
 * Time: 6:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ToolParameter
{
  private String parameterName;
  private String parameterValue;

  public ToolParameter(String parameterName, String parameterValue)
  {
    this.parameterName = parameterName;
    this.parameterValue = parameterValue;
  }

  @JsonProperty("param")
  public String getParameterName()
  {
    return parameterName;
  }

  public void setParameterName(String parameterName)
  {
    this.parameterName = parameterName;
  }

  @JsonProperty("value")
  public String getParameterValue()
  {
    return parameterValue;
  }

  public void setParameterValue(String parameterValue)
  {
    this.parameterValue = parameterValue;
  }
}


