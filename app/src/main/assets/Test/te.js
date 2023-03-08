const url = 'https://provinces.open-api.vn/api/';
const select = document.getElementById('city');
let selectedCity; // Khai báo biến selectedCity ở phạm vi bên ngoài fetch

function test(){
    fetch(url)
    .then(response => response.json())
    .then(data => {
        const cities = data;
        cities.forEach(city => {
            const option = document.createElement('option');
            option.value = city.name;
            option.text = city.name;
            select.appendChild(option);
        });
        select.addEventListener('change', () => {
            selectedCity = select.value; // Gán giá trị cho biến selectedCity trong hàm change
        });
    })
    .catch(error => console.error(error));
}

function Kali() {
    test();
    const promise = new Promise(resolve => {
        const checkSelectedCity = () => {
            if (selectedCity) {
                resolve(selectedCity);
            } else {
                setTimeout(checkSelectedCity, 100);
            }
        };
        checkSelectedCity();
    });
    promise.then(city => {
        console.log(`Đã chọn tỉnh/thành phố: ${city}`);
    });
}

Kali()