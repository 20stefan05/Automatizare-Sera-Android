package com.example.automatizaresera;

public class SelectedPlant {
    int _id;
    String _name;
    String _image;
    int _maxLightLux;
    int _minLightLux;
    int _maxTemp;
    int _minTemp;
    int _maxEnvHumid;
    int _minEnvHumid;
    int _maxSoilMoist;
    int _minSoilMoist;
    int _maxSoilEc;
    int _minSoilEc;
    public SelectedPlant(){   }
    public SelectedPlant(int id, String name, String image, int maxLightLux, int minLightLux, int maxTemp,
                         int minTemp, int maxEnvHumid, int minEnvHumid, int maxSoilMoist, int minSoilMoist, int maxSoilEc,
                         int minSoilEc){
        this._id = id;
        this._name = name;
        this._image= image;
        this._maxLightLux = maxLightLux;
        this._minLightLux = minLightLux;
        this._maxTemp = maxTemp;
        this._minTemp = minTemp;
        this._maxEnvHumid = maxEnvHumid;
        this._minEnvHumid = minEnvHumid;
        this._maxSoilMoist = maxSoilMoist;
        this._minSoilMoist = minSoilMoist;
        this._maxSoilEc = maxSoilEc;
        this._minSoilEc = minSoilEc;
    }
    public SelectedPlant( String name, String image, int maxLightLux, int minLightLux, int maxTemp,
                         int minTemp, int maxEnvHumid, int minEnvHumid, int maxSoilMoist, int minSoilMoist, int maxSoilEc,
                         int minSoilEc){

        this._name = name;
        this._image= image;
        this._maxLightLux = maxLightLux;
        this._minLightLux = minLightLux;
        this._maxTemp = maxTemp;
        this._minTemp = minTemp;
        this._maxEnvHumid = maxEnvHumid;
        this._minEnvHumid = minEnvHumid;
        this._maxSoilMoist = maxSoilMoist;
        this._minSoilMoist = minSoilMoist;
        this._maxSoilEc = maxSoilEc;
        this._minSoilEc = minSoilEc;
    }


    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getImage(){
        return this._image;
    }

    public void setImage(String image){
        this._image = image;
    }
    public int getMaxLightLux(){
        return this._maxLightLux;
    }

    public void setMaxLightLux(int maxLightLux){
        this._maxLightLux = maxLightLux;
    }
    public int getMinLightLux(){
        return this._minLightLux;
    }

    public void setMinLightLux(int minLightLux){
        this._minLightLux = minLightLux;
    }
    public int getMaxTemp(){
        return this._maxTemp;
    }

    public void setMaxTemp(int maxTemp){
        this._maxTemp = maxTemp;
    }
    public int getMinTemp(){
        return this._minTemp;
    }

    public void setMinTemp(int minTemp){
        this._minTemp = minTemp;
    }
    public int getMaxEnvHumid(){
        return this._maxEnvHumid;
    }

    public void setMaxEnvHumid(int maxEnvHumid){
        this._maxEnvHumid = maxEnvHumid;
    }
    public int getMinEnvHumid(){
        return this._minEnvHumid;
    }

    public void setMinEnvHumid(int minEnvHumid){
        this._minEnvHumid = minEnvHumid;
    }
    public int getMaxSoilMoist(){
        return this._maxSoilMoist;
    }

    public void setMaxSoilMoist(int maxSoilMoist){
        this._maxSoilMoist = maxSoilMoist;
    }
    public int getMinSoilMoist(){
        return this._minSoilMoist;
    }

    public void setMinSoilMoist(int minSoilMoist){
        this._minSoilMoist = minSoilMoist;
    }
    public int getMaxSoilEc(){
        return this._maxSoilEc;
    }

    public void setMaxSoilEc(int maxSoilEc){
        this._maxSoilEc = maxSoilEc;
    }
    public int getMinSoilEc(){
        return this._minSoilEc;
    }

    public void setMinSoilEc(int minSoilEc){
        this._minSoilEc = minSoilEc;
    }
}
