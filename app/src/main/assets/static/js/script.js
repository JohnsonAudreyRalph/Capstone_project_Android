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
    // Lấy dữ liệu thời gian thực
    function GETTIME(){
        const now = new Date();
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');
        const formattedTime = `${hours}:${minutes}`;
        document.getElementById('Day').innerHTML = day;
        document.getElementById('Month').innerHTML = month;
        document.getElementById('Year').innerHTML = year;
        document.getElementById('Time').innerHTML = formattedTime;
    }
    setInterval(GETTIME, 3);

    function Clock(){
        var ClockHour = document.getElementById('clock_hour');
        var ClockHour_12 = document.getElementById('clock_hour_12');
        var ClockMinute = document.getElementById('clock_minute');
        var ClockSecond = document.getElementById('clock_second');
        var ARM = document.getElementById('APM');
        var lastDate = [];
        const date = new Date();
        lastDate['H'] = date.getHours();
        lastDate['M'] = date.getMinutes();
        lastDate['S'] = date.getSeconds();

        console.log(lastDate['H'])
        function newNumber(elem, num){
            if(elem){
                elem.classList.add('clock_item--active');

                setTimeout(function(){
                    elem.classList.remove('clock_item--active');
                    if(num >= 10){
                        elem.innerHTML = num;
                    }else{
                        elem.innerHTML = '0' + num;
                    }
                }, 200);
            }
        }

        newNumber(ClockHour, lastDate['H']);
        newNumber(ClockMinute, lastDate['M']);
        newNumber(ClockSecond, lastDate['S']);

        setInterval(function(){
            const date = new Date();
            if(lastDate['H'] != date.getHours()){
                lastDate['H'] = date.getHours();
                newNumber(ClockHour, lastDate['H']);
                console.log('Thời gian là: ')
                console.log(lastDate['H'])
                
            }
            if(lastDate['M'] != date.getMinutes()){
                lastDate['M'] = date.getMinutes();
                newNumber(ClockMinute, lastDate['M']);
            }
            if(lastDate['S'] != date.getSeconds()){
                lastDate['S'] = date.getSeconds();
                newNumber(ClockSecond, lastDate['S']);
            }
            if(lastDate['H'] > 12){
                ARM.innerHTML = 'PM'
                console.log('Đã đổi thời gian mới')
                ClockHour_12.innerHTML = lastDate['H'] - 12
            }
            if(lastDate['H'] <= 12){
                ARM.innerHTML = 'AM'
                console.log('Sai ở đâu đó')
                ClockHour_12.innerHTML = lastDate['H']
            }
            if((lastDate['H'] - 12) < 10){
                Temp = lastDate['H'] - 12
                ClockHour_12.innerHTML = '0' + Temp
            }
        }, 10)
    }
    Clock()

    $('#calender').datetimepicker({
        inline: true,
        format: 'L'
    });
    var previousDateString = null;
    var dateString = null;

    function handleDateChange() {
        dateString = document.getElementById('calender').getElementsByClassName('active')[0].getAttribute('data-day');
        if (dateString !== previousDateString) {
            previousDateString = dateString;
            clickLoadDay(previousDateString);
        }
    }
    // setInterval(handleDateChange, 3);

    function clickLoadDay(dateString) {
        var DATE = dateString.split('/')[1] + '/' + dateString.split('/')[0] + '/' + dateString.split('/')[2];
        console.log(DATE);
        Android.getDataFromJavascript(DATE);
    }

    var button = document.getElementById("Click");
    button.addEventListener("click", function() {
        handleDateChange()
    });
});