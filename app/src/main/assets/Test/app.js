var citis = document.getElementById("city"); 

var Parameter = {
    url: 'https://provinces.open-api.vn/api/',//Đường dẫn đến file chứa dữ liệu hoặc api do backend cung cấp
    method:'GET', //do backend cung cấp 
    responseType: 'application/json', //kiểu Dữ liệu trả về do backend cung cấp
}
//gọi ajax = axios => nó trả về cho chúng ta là một promise
var promise = axios(Parameter);
//Xử lý khi request thành công
promise.then(function(result) {
    renderCity(result.data);
});

function renderCity(data){
    for (const x of data) {
        citis.options[citis.options.length] = new Option(x.name, x.code);
        console.log(x.name)
    }
}