# API

 - [GET] /  
 > Return all histories  

 ```
 [
  {
    "id": 1,
    "name": "captan oh my dear captan",
    "latitude": "19.4379897",
    "longitude": "-99.137699",
    "url": "/Users/evesan/MEGA/Everito.san/creatn/Api/public/uploads/2016_20_08_21_57_55-CDTWar Of Colossus.mp3",
    "likes": 1,
    "created_at": "2016-08-21T02:57:55.457Z",
    "updated_at": "2016-08-21T03:20:50.712Z",
    "tags": []
  }
]
 ```

 - [POST] /history   
 > Creates a new History.   

 Params required into and history Object:  
  - [:name]  
    String name of the audio  
  - [:latitude]  
    String latitude for the audio  
  - [:longitude]  
    String longitude for the audio  
  - [:audio]  
    Audio File

```
{
  "id": 2,
  "name": "captan oh my dear captan",
  "latitude": "19.4379897",
  "longitude": "-99.137699",
  "url": "/Users/evesan/MEGA/Everito.san/creatn/Api/public/uploads/2016_21_08_00_51_59-CDTWar Of Colossus.mp3",
  "likes": 0,
  "created_at": "2016-08-21T05:51:59.677Z",
  "updated_at": "2016-08-21T05:51:59.677Z",
  "tags": []
}
```
