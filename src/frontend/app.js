const taxRatesList = document.getElementById('taxRatesList');
const addTaxRateForm = document.getElementById('addTaxRateForm');

fetch('http://localhost:8081/api/taxRates')
    .then(response => response.json())
    .then(data => {
        data.forEach(taxRate => {
            const taxRateItem = document.createElement('div');
            taxRateItem.innerHTML = `<b>State:</b> ${taxRate.state}, <b>City:</b> ${taxRate.city}, <b>Rate:</b> ${taxRate.rate}%`;
            taxRatesList.appendChild(taxRateItem);
        });
    });

addTaxRateForm.addEventListener('submit', function(event) {
    event.preventDefault();
    const formData = new FormData(this);
    const newTaxRate = {
        state: formData.get('state'),
        city: formData.get('city'),
        rate: formData.get('rate')
    };

    fetch('http://localhost:8081/api/taxRates', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newTaxRate)
    })
    .then(response => response.json())
    .then(data => {
        const taxRateItem = document.createElement('div');
        taxRateItem.innerHTML = `<b>State:</b> ${data.state}, <b>City:</b> ${data.city}, <b>Rate:</b> ${data.rate}%`;
        taxRatesList.appendChild(taxRateItem);
        addTaxRateForm.reset();
    })
    .catch(error => console.error('Error adding tax rate:', error));
});
