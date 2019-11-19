# Instructions
In order to succesffully complete this project you develop end-user mobile application based on RTEPMS platform. RTEPMS (Real-Time Environmental Parameters Monitoring System) represents general-purpose environmental parameters monitoring system.

### Objective
The main idea behind this project is to utilize data from RTEPMS platform and develop end-user mobile application that will show weather and swimming conditions at the most popular beaches.
The application should be also able to display warning in case that swimming conditions are not OK.

### Resources

For the mock data you can use existing RTEPMS endpoints for UDG Meteo stations.

```
http://rtepms.udg.edu.me:8000/api/measurement_by_date/?node_id=3&start_date=19/11/2019&end_date=19/11/2019
```
**Note: If you are not able to resolve rtepms.udg.edu.me domain use IP address: `213.149.113.86` like this `http://213.149.113.86:8000`.

**Params**
  - `node_id` - Public Node ID
  - `start_date` - Get sensor measurements from this date
  - `end_date` - Get sensor measurments till this date. (Optional - if not passed end_date will be `till today`)
  
**Available Nodes**

Node ID `3` -> `UDG Meteo Station - Air info`
  
- `sensor_1_val` -> `Air Temperature  [C]`
- `sensor_2_val` -> `Air Humidity [%]`
- `sensor_3_val` -> `Air Pressure [mbar]`

Node ID `4` -> `UDG Meteo Station - Wind info`
  
- `sensor_1_val` -> `Average Wind Speed (1min) [m/s]`
- `sensor_2_val` -> `Maximum Wind Speed (5min) [m/s]`
- `sensor_3_val` -> `Wind Direction deg°`
   
Node ID `5` -> `UDG Meteo Station - Rainfall info`
  
- `sensor_1_val` -> `Rainfall (1h) [mm]`
- `sensor_2_val` -> `Rainfall (24h) [mm]`
  
**Additional Endpoint**
You can use another API endpoint to fetch only several (`limit`) last measurements.
```
http://rtepms.udg.edu.me:8000/api/measurement_by_limit/?node_id=3&limit=5
```
**Note**

_Please note that historical data might containg testing data as platform is still in development mode._


