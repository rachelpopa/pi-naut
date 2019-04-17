package pi.naut.devlight;

public class LightState {
  private String alert;
  private int bri;
  private String colormode;
  private int ct;
  private String effect;
  private int hue;
  private boolean on;
  private boolean reachable;
  private int sat;
  private int transitiontime;
  private double[] xy;

  public String getAlert() {
    return alert;
  }

  public void setAlert(String alert) {
    this.alert = alert;
  }

  public int getBri() {
    return bri;
  }

  public void setBri(int bri) {
    this.bri = bri;
  }

  public String getColormode() {
    return colormode;
  }

  public void setColormode(String colormode) {
    this.colormode = colormode;
  }

  public int getCt() {
    return ct;
  }

  public void setCt(int ct) {
    this.ct = ct;
  }

  public String getEffect() {
    return effect;
  }

  public void setEffect(String effect) {
    this.effect = effect;
  }

  public int getHue() {
    return hue;
  }

  public void setHue(int hue) {
    this.hue = hue;
  }

  public boolean isOn() {
    return on;
  }

  public void setOn(boolean on) {
    this.on = on;
  }

  public boolean isReachable() {
    return reachable;
  }

  public void setReachable(boolean reachable) {
    this.reachable = reachable;
  }

  public int getSat() {
    return sat;
  }

  public void setSat(int sat) {
    this.sat = sat;
  }

  public int getTransitiontime() {
    return transitiontime;
  }

  public void setTransitiontime(int transitiontime) {
    this.transitiontime = transitiontime;
  }

  public double[] getXy() {
    return xy;
  }

  public void setXy(double[] xy) {
    this.xy = xy;
  }
}
