let Day_time = [];
let Day_temperature = [];
let Day_relativehumidity = [];

function Change_Values(obj){
    var city = obj.value;
    GetJSON(city);
}

function GetJSON(city){
    // console.log(city)
    const URL = "https://api.openweathermap.org/data/2.5/weather?q=" + city +"&appid=1ce7185d18f8940b4093013365c81a21"
    fetch(URL)
    .then(response => response.json())
    .then(data =>{
        lon = data['coord']['lon']
        lat = data['coord']['lat']
        // console.log(URL);
        const now = new Date();
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const new_DAY = `${year}-${month}-${day}`;
        const getLink = "https://api.open-meteo.com/v1/forecast?latitude=" + lat +"&longitude=" + lon + "&hourly=temperature_2m,relativehumidity_2m,weathercode&current_weather=true&timezone=Asia%2FBangkok&start_date=" + new_DAY + "&end_date=" + new_DAY
        console.log(getLink)
        URL_JSON = getLink;
        fetch(URL_JSON)
        .then(response => response.json())
        .then(Json =>{
            temperature = Json["current_weather"]["temperature"]; // Nhiệt độ
            windspeed = Json["current_weather"]["windspeed"]; // Tốc độ gió
            weathercode = Json["current_weather"]["weathercode"]; // Mã thời tiết

            TIME = Json['hourly']['time']
            // console.log(TIME)
            Day_time = TIME.map(time => time + ':00.000Z');
            console.log(Day_time);
            Day_temperature = Json['hourly']['temperature_2m']
            Day_relativehumidity = Json['hourly']['relativehumidity_2m']
            renderChart();

            // var NhietDo = document.getElementById('NhietDo');
            document.getElementById('NhietDo').innerHTML = temperature;
            document.getElementById('TocDoGio').innerHTML = windspeed;

            if(weathercode == 0){
                StringWeather = "Trời quang đãng";
            }
            else if(weathercode == 1 || weathercode == 2 || weathercode == 3){
                StringWeather = "Có mây và u ám";
            }
            else if(weathercode == 45 || weathercode == 48){
                StringWeather = "Có sương mù lắng đọng";
            }
            else if(weathercode == 51 || weathercode == 53 || weathercode == 55){
                StringWeather = "Có mưa phùn";
            }
            else if(weathercode == 56 || weathercode == 57){
                StringWeather = "Có mưa phùn, đóng băng";
            }
            else if(weathercode == 61 || weathercode == 63 || weathercode == 65){
                StringWeather = "Có mưa nhẹ";
            }
            else if(weathercode == 66 || weathercode == 67){
                StringWeather = "Có mưa đóng băng";
            }
            else if(weathercode == 71 || weathercode == 73 || weathercode == 75){
                StringWeather = "Có tuyết rơi";
            }
            else if(weathercode == 80 || weathercode == 81 || weathercode == 82){
                StringWeather = "Mưa rào";
            }
            else if(weathercode == 85 || weathercode == 86){
                StringWeather = "Mưa tuyết nhẹ và nặng";
            }
            else if(weathercode == 95 || weathercode == 96 || weathercode == 99){
                StringWeather = "Giông bão";
            }
            else{
                StringWeather = "Lỗi gì đó";
            }
            document.getElementById('TrangThaiThoiTiet').innerHTML = StringWeather;
        })
    })   
}

function renderChart(){
    Promise.all([Day_time, Day_temperature, Day_relativehumidity])
    .then(([time, temperatureData, humidityData]) => {
        new ApexCharts(document.querySelector("#reportsChart"), {
            series: [{
                name: 'Nhiệt độ',
                data: temperatureData,
            }, {
                name: 'Độ ẩm',
                data: humidityData
            }],
            chart: {
                height: 350,
                type: 'area',
                toolbar: {
                show: false
                },
            },
            markers: {
                size: 4
            },
            colors: ['#4154f1', '#2eca6a', '#ff771d'],
            fill: {
                type: "gradient",
                gradient: {
                shadeIntensity: 1,
                opacityFrom: 0.3,
                opacityTo: 0.4,
                stops: [0, 90, 100]
                }
            },
            dataLabels: {
                enabled: false
            },
            stroke: {
                curve: 'smooth',
                width: 2
            },
            xaxis: {
                type: 'datetime',
                categories: time
            },
            tooltip: {
                x: {
                format: 'dd/MM/yy HH:mm'
                },
            }
        }).render();
    })
}