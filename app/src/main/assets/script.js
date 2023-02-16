$(document).ready(function(){
    // Tạo 1 hàm gọi, sau 100 milisecond thì mới hiện các nội dung bên trong
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 100);
    };
    spinner();

    // Tạo thanh thay đổi điều hướng tác vụ
    $('.sidebar-toggler').click(function () {
        $('.sidebar, .content').toggleClass("open");
        return false;
    });

    // Lấy dữ liệu thời tiết
    const getLink = "https://api.open-meteo.com/v1/forecast?latitude=21.59&longitude=105.85&hourly=temperature_2m,relativehumidity_2m,weathercode&current_weather=true&timezone=Asia%2FBangkok"
    function GetJSON(){
        URL_JSON = getLink;
        fetch(URL_JSON)
          .then(response => response.json())
          .then(Json =>{
            temperature = Json["current_weather"]["temperature"]; // Nhiệt độ
            winddirection = Json["current_weather"]["winddirection"]; // Độ ẩm
            weathercode = Json["current_weather"]["weathercode"]; // Mã thời tiết
            time = Json["current_weather"]["time"]; // Thời gian

            // console.log(time);
            var Split_Time = time.split("T")[0]; // Tách được thời gian với ngày tháng ==> Lấy được ngày tháng
            var Take_The_Time = time.split("T")[1]; // Lấy được thời gian
            var Get_Year = Split_Time.split("-")[0]; // Lấy được năm
            var Get_Month = Split_Time.split("-")[1]; // Còn lại tháng
            var Get_Day = Split_Time.slice(8); // Lấy được ngày
            document.getElementById('Time').innerHTML = Take_The_Time;
            document.getElementById('Day').innerHTML = Get_Day;
            document.getElementById('Month').innerHTML = Get_Month;
            document.getElementById('Year').innerHTML = Get_Year;

            // var NhietDo = document.getElementById('NhietDo');
            document.getElementById('NhietDo').innerHTML = temperature;
            document.getElementById('DoAm').innerHTML = winddirection;

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
    }

    setInterval(GetJSON, 300);
    // setInterval(GetJSON, 30000);
    // Vẽ biểu đồ hình cột
    // var ctx = document.getElementById('myChart').getContext('2d');
    // // Lấy dữ liệu cho biểu đồ
    // var data = {
    //     labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6'],
    //     datasets: [{
    //         label: 'Doanh thu',
    //         data: [1200, 1500, 1000, 1700, 1300, 2000],
    //         backgroundColor: [
    //             'rgba(255, 99, 132, 0.2)',
    //             'rgba(54, 162, 235, 0.2)',
    //             'rgba(255, 206, 86, 0.2)',
    //             'rgba(75, 192, 192, 0.2)',
    //             'rgba(153, 102, 255, 0.2)',
    //             'rgba(255, 159, 64, 0.2)'
    //         ],
    //         borderColor: [
    //             'rgba(255, 99, 132, 1)',
    //             'rgba(54, 162, 235, 1)',
    //             'rgba(255, 206, 86, 1)',
    //             'rgba(75, 192, 192, 1)',
    //             'rgba(153, 102, 255, 1)',
    //             'rgba(255, 159, 64, 1)'
    //         ],
    //         borderWidth: 1
    //     }]
    // };
    // // Tùy chọn biểu đồ
    // var options = {
    //     scales: {
    //         yAxes: [{
    //             ticks: {
    //                 beginAtZero: true
    //             }
    //         }]
    //     }
    // };
    // // Khởi tạo biểu đồ cột
    // var myChart = new Chart(ctx, {
    //     type: 'bar',
    //     data: data,
    //     options: options
    // });
});