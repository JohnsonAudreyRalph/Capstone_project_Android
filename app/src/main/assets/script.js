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
});