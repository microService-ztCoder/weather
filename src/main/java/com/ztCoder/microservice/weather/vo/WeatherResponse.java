package com.ztCoder.microservice.weather.vo;

import java.io.Serializable;

/**
 * Weather Response
 * 
 * @author ztCoder
 *
 */
public class WeatherResponse implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Weather weather;
  private Integer status;
  private String desc;

  public Weather getWeather() {
    return weather;
  }

  public void setWeather(Weather weather) {
    this.weather = weather;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
