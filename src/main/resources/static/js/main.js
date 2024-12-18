// 處理登入和登出的邏輯
function handleLoginLogout() {
    const loginButton = document.getElementById('loginButton');
    
    // 檢查按鈕文字，判斷用戶是否已登入
    if (loginButton.innerText === '登入') {
        // 如果用戶尚未登入，跳轉至登入頁面
        window.location.href = '/log_in';
    } else {
        // 如果用戶已登入，執行登出操作
        // 發送登出請求給後端
        fetch('/logout', { method: 'GET' })
            .then(response => {
                if (response.ok) {
                    // 登出成功，更新按鈕文字並清除登入狀態
                    loginButton.innerText = '登入';
                    localStorage.removeItem('loggedIn');
                    alert("已登出！");
                }
            });
    }
}

// 頁面加載完成後，更新登入或登出的按鈕狀態
document.addEventListener("DOMContentLoaded", function() {
    const loginButton = document.getElementById('loginButton');

    // 向後端請求當前登入狀態
    fetch('/login-status') // 需要在後端實現該端點來返回登入狀態
        .then(response => response.json())
        .then(data => {
            // 根據後端返回的登入狀態更新按鈕文字
            if (data.loggedIn) {
                loginButton.innerText = '登出';
            } else {
                loginButton.innerText = '登入';
            }
        });
});
