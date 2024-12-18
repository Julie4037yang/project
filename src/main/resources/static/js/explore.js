// 登入登出邏輯
function handleLoginLogout() {
    const loginButton = document.getElementById('loginButton');
    if (loginButton.innerText === '登入') {
        window.location.href = '/log_in';  // 轉到登入頁面
    } else {
        fetch('/logout', { method: 'GET' })  // 發送登出請求
            .then(response => {
                if (response.ok) {
                    loginButton.innerText = '登入';  // 更新按鈕文字為登入
                    localStorage.removeItem('loggedIn');  // 清除本地存儲的登入狀態
                    alert("已登出！");  // 顯示登出提示
                }
            });
    }
}

// 加載時更新登入狀態
document.addEventListener("DOMContentLoaded", function() {
    const loginButton = document.getElementById('loginButton');
    fetch('/login-status')  // 獲取當前登入狀態
        .then(response => response.json())
        .then(data => {
            if (data.loggedIn) {
                loginButton.innerText = '登出';  // 若已登入，顯示登出
            } else {
                loginButton.innerText = '登入';  // 若未登入，顯示登入
            }
        });
});