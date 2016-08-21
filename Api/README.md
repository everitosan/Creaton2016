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
    "url": "/Api/public/uploads/2016_20_08_21_57_55-CDTWar Of Colossus.mp3",
    "likes": 1,
    "created_at": "2016-08-21T02:57:55.457Z",
    "updated_at": "2016-08-21T03:20:50.712Z",
    "tags": []
  }
]
 ```

 - [POST] /history   
 > Creates a new History.   

 Params required into and *history* Object:  
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
  "url": "/Api/public/uploads/2016_21_08_00_51_59-CDTWar Of Colossus.mp3",
  "likes": 0,
  "created_at": "2016-08-21T05:51:59.677Z",
  "updated_at": "2016-08-21T05:51:59.677Z",
  "tags": []
}  
```  

- [POST] /history/like  
> Likes a history.   

Params required into and *history* Object:  
 - [:id]  
   Id of the audio to like  

```
{
  "id": 1,
  "name": "captan oh my dear captan",
  "latitude": "19.4379897",
  "longitude": "-99.137699",
  "url": "/Api/public/uploads/2016_20_08_21_57_55-CDTWar Of Colossus.mp3",
  "likes": 2,
  "created_at": "2016-08-21T02:57:55.457Z",
  "updated_at": "2016-08-21T05:55:54.761Z"
}
```


- [POST] /history/search  
> Return nearby audios.   

Params required into and *user_info* Object:  
 - [:longitude]  
   Longitude of the user  
 - [:latitude]  
   Latitude of the user  
 - [:purged]  
   Flag that determines the mode of search  

```
[
  {
    "id": 2,
    "name": "captan oh my dear captan",
    "latitude": "19.4379897",
    "longitude": "-99.137699",
    "url": "/Api/public/uploads/2016_21_08_00_51_59-CDTWar Of Colossus.mp3",
    "likes": 0,
    "created_at": "2016-08-21T05:51:59.677Z",
    "updated_at": "2016-08-21T05:51:59.677Z",
    "tags": []
  },
  {
    "id": 1,
    "name": "captan oh my dear captan",
    "latitude": "19.4379897",
    "longitude": "-99.137699",
    "url": "/Api/public/uploads/2016_20_08_21_57_55-CDTWar Of Colossus.mp3",
    "likes": 2,
    "created_at": "2016-08-21T02:57:55.457Z",
    "updated_at": "2016-08-21T05:55:54.761Z",
    "tags": []
  }
]
```
