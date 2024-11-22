function formatPrice(price) {
    return price.toLocaleString('vi-VN') + ' VNĐ';
}

function cleanAndParsePrice(priceElement) {
    // Clean the text by removing non-numeric characters except periods (.) and hyphens (-)
    const priceText = priceElement.textContent.replace(/[^\d.,-]/g, '');
    // Remove any commas used as thousand separators
    const cleanPrice = priceText.replace(/,/g, '');
    // Parse the cleaned string into a float
    const parsedPrice = parseFloat(cleanPrice);
    // Return NaN if the parsed value is invalid, so we can handle it later
    return isNaN(parsedPrice) ? 0 : parsedPrice;
}

// Apply formatting to all original and sale prices on page load
document.addEventListener('DOMContentLoaded', function () {
    const originalPrices = document.querySelectorAll('.priceOriginal');
    const salePrices = document.querySelectorAll('.priceSale');

    originalPrices.forEach(priceElement => {
        const price = cleanAndParsePrice(priceElement);
        priceElement.textContent = formatPrice(price);
    });

    salePrices.forEach(priceElement => {
        const price = cleanAndParsePrice(priceElement);
        priceElement.textContent = formatPrice(price);
    });
});


document.getElementById('shop-button').addEventListener('click', function (event) {
    const form = document.getElementById('customer-form');

    if (form.style.display === 'block') {
        form.style.display = 'none';
    } else {
        form.style.display = 'block';
    }
});


document.getElementById('shop-button-search').addEventListener('click', function (event) {
    const form = document.getElementById('customer-form');
    form.style.display = 'none';

});

document.addEventListener('click', function (event) {
    const form = document.getElementById('customer-form');
    const shopButton = document.getElementById('shop-button');

    if (form.style.display === 'block' && !form.contains(event.target) && !shopButton.contains(event.target)) {
        form.style.display = 'none';
    }
});


document.addEventListener('click', function (event) {
    const form = document.getElementById('customer-form');
    const shopButton = document.getElementById('shop-button');

    if (form.style.display === 'block' && !form.contains(event.target) && !shopButton.contains(event.target)) {
        form.style.display = 'none';
    }
});


// Kiểm tra localStorage để thiết lập trạng thái ban đầu của form
window.addEventListener('load', function () {
    const form = document.getElementById('customer-form');
    const isFormVisible = localStorage.getItem('isFormVisible');

    if (isFormVisible === 'true') {
        form.style.display = 'block';
    } else {
        form.style.display = 'none';
    }
});

// Chuyển đổi hiển thị của form và lưu trạng thái vào localStorage
document.getElementById('shop-button').addEventListener('click', function (event) {
    const form = document.getElementById('customer-form');

    // Chuyển đổi hiển thị
    if (form.style.display === 'block') {
        form.style.display = 'none';
        localStorage.setItem('isFormVisible', 'false'); // Lưu trạng thái
    } else {
        form.style.display = 'block';
        localStorage.setItem('isFormVisible', 'true'); // Lưu trạng thái
    }
});

// Ẩn form khi nhấp bên ngoài form
document.addEventListener('click', function (event) {
    const form = document.getElementById('customer-form');
    const shopButton = document.getElementById('shop-button');

    if (form.style.display === 'block' && !form.contains(event.target) && !shopButton.contains(event.target)) {
        form.style.display = 'none';
        localStorage.setItem('isFormVisible', 'false'); // Lưu trạng thái
    }
});













