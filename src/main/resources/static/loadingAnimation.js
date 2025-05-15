// Loading screen animation
window.addEventListener('load', function() {
    setTimeout(function () {
        const loadingScreen = document.querySelector('.loading-screen');
        loadingScreen.style.opacity = '0';
        setTimeout(function () {
            loadingScreen.style.display = 'none';
        }, 500);
    }, 1500);
});