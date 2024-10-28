function formatPrice(price) {
    return price.toLocaleString('vi-VN') + 'đ'; // Format price for Vietnamese locale and add currency symbol
}

function cleanAndParsePrice(priceElement) {
    // Lấy giá trị văn bản của phần tử và loại bỏ tất cả các ký tự không phải là số hoặc dấu thập phân
    const priceText = priceElement.textContent.replace(/[^\d,-]/g, '');
    // Đổi dấu phẩy thành dấu chấm nếu cần để phù hợp với format của parseFloat
    const cleanPrice = priceText.replace(/,/g, '');
    // Chuyển đổi chuỗi đã làm sạch thành kiểu số thực (float)
    const parsedPrice = parseFloat(cleanPrice);
    // Trả về 0 nếu giá trị không hợp lệ
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



document.getElementById('shop-button').addEventListener('click', function(event) {
    const form = document.getElementById('customer-form');

    if (form.style.display === 'block') {
        form.style.display = 'none';
    } else {
        form.style.display = 'block';
    }
});

document.addEventListener('click', function(event) {
    const form = document.getElementById('customer-form');
    const shopButton = document.getElementById('shop-button');

    if (form.style.display === 'block' && !form.contains(event.target) && !shopButton.contains(event.target)) {
        form.style.display = 'none';
    }
});

















