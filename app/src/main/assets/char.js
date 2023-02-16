function GetJSONData(){
    URL_JSON = "https://api.open-meteo.com/v1/forecast?latitude=21.59&longitude=105.85&hourly=temperature_2m,relativehumidity_2m&current_weather=true&timezone=Asia%2FBangkok&start_date=2023-02-16&end_date=2023-02-16";
    fetch(URL_JSON)
        .then(response => response.json())
        .then(Json =>{
            time = Json["hourly"]["time"]; // Thời gian
            temperature = Json["hourly"]["temperature_2m"]; // Thời gian
            humidity = Json["hourly"]["relativehumidity_2m"]; // Thời gian
            // console.log(time);
            let Data_Time = [];
            let Data_Temperature = [];
            let Data_Humidity = [];
            time.forEach(time => {
                Data_Time.push(time);
            });
            temperature.forEach(temperature => {
                Data_Temperature.push(temperature);
            });
            humidity.forEach(humidity => {
                Data_Humidity.push(humidity);
            });
        // alert(Data_Time)
        console.log(Data_Time);
        console.log(Data_Temperature);
        console.log(Data_Humidity);
    })
}

document.addEventListener("DOMContentLoaded", () => {
    setInterval(GetJSONData, 300);
    new ApexCharts(document.querySelector("#reportsChart"), {
        series: [{
            name: 'Nhiệt độ',
            data: [31, 40, 28, 51, 42, 82, 56],
        }, {
            name: 'Độ ẩm',
            data: [11, 32, 45, 32, 34, 52, 41]
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
            categories: ["2022-10-19T00:00:00.000Z", "2022-06-19T01:30:00.000Z", "2022-02-19T02:30:00.000Z", "2021-10-19T03:30:00.000Z", "2021-6-19T04:30:00.000Z", "2021-02-19T05:30:00.000Z", "2020-10-19T06:30:00.000Z"]
        },
        tooltip: {
            x: {
            format: 'dd/MM/yy HH:mm'
            },
        }
    }).render();
});