document.addEventListener("DOMContentLoaded", function () {
    // DOM 元素引用
    const loginButton = document.getElementById('loginButton');
    const registrationButton = document.getElementById('registrationButton');
    const cancelRegistrationButton = document.getElementById('cancelRegistration');
    const registrationModal = document.getElementById('registrationModal');
    const registeredCountElement = document.getElementById('registeredCount');
    const eventId = window.location.pathname.split('/').pop(); // 取得活動 ID
    let isRegistered = false; // 初始報名狀態，默認為未報名
    let isFull = false; // 初始活動是否額滿，默認為否

    // 更新 UI 以反映當前報名狀態
    function updateUI(registeredCount, capacity, isRegistered, isFull) {
        // 更新已報名人數顯示
        if (registeredCountElement) {
            registeredCountElement.textContent = `${registeredCount} / ${capacity}`;
        }

        // 根據報名狀態更新按鈕
        if (isRegistered) {
            registrationButton.innerText = '已報名 !';
            registrationButton.disabled = true;
            registrationButton.style.backgroundColor = 'grey';
            cancelRegistrationButton.style.display = 'inline'; // 顯示取消報名按鈕
            cancelRegistrationButton.disabled = false; // 允許取消報名
        } else if (isFull) {
            registrationButton.innerText = '已額滿';
            registrationButton.disabled = true;
            registrationButton.style.backgroundColor = 'grey';
            cancelRegistrationButton.style.display = 'inline'; // 顯示取消報名按鈕
            cancelRegistrationButton.disabled = true; // 禁止取消報名
        } else {
            registrationButton.innerText = '報名';
            registrationButton.disabled = false;
            registrationButton.style.backgroundColor = 'rgb(226, 238, 252)';
            cancelRegistrationButton.style.display = 'inline'; // 顯示取消報名按鈕
            cancelRegistrationButton.disabled = true; // 禁止取消報名
        }
    }

    // 刷新活動詳情，更新報名人數和活動狀態
    function refreshEventDetails() {
        fetch(`/event-details/${eventId}`)
            .then(response => response.json())
            .then(data => {
                isFull = data.registeredCount >= data.capacity; // 判斷活動是否已額滿
                updateUI(data.registeredCount, data.capacity, isRegistered, isFull); // 更新 UI
            })
            .catch(err => {
                console.error('刷新活動詳情失敗：', err);
            });
    }

    // 初始化登入狀態
    fetch('/login-status')
        .then(response => response.json())
        .then(data => {
            if (data.loggedIn) {
                loginButton.innerText = '登出';
                loginButton.onclick = handleLogout; // 登出處理
            } else {
                loginButton.innerText = '登入';
                loginButton.onclick = () => window.location.href = '/log_in'; // 跳轉至登入頁面
            }

            // 初始化報名狀態
            fetch(`/check-registration/${eventId}`)
                .then(response => response.json())
                .then(registrationData => {
                    isRegistered = registrationData.isRegistered; // 獲取報名狀態
                    localStorage.setItem(`isRegistered_${eventId}`, isRegistered); // 更新 localStorage
                    refreshEventDetails();  // 刷新活動詳情
                })
                .catch(err => {
                    console.error('報名狀態初始化失敗：', err);
                });
        })
        .catch(err => {
            console.error('登入狀態初始化失敗：', err);
        });

    // 頁面加載時刷新活動詳情
    refreshEventDetails();

    // 處理登出操作
    function handleLogout() {
        fetch('/logout')
            .then(() => {
                alert('已登出 !');
                window.location.href = '/';
            })
            .catch(() => {
                alert('登出失敗，請稍後再試！');
            });
    }

    // 點擊報名按鈕時顯示報名視窗
    registrationButton.addEventListener('click', function () {
        fetch('/login-status')
            .then(response => response.json())
            .then(data => {
                if (!data.loggedIn) {
                    alert('請先登入/註冊，才能進行報名。');
                    window.location.href = '/log_in'; // 跳轉至登入頁面
                } else {
                    registrationModal.style.display = 'block'; // 顯示報名視窗
                }
            });
    });

    // 關閉報名視窗
    document.getElementById('closeModal').addEventListener('click', () => {
        registrationModal.style.display = 'none'; // 隱藏報名視窗
    });

    // 提交報名表單
    document.getElementById('registrationForm').addEventListener('submit', function (event) {
        event.preventDefault(); // 防止表單默認提交
        const formData = new FormData(this);

        fetch(`/register/${eventId}`, {
            method: 'POST',
            body: new URLSearchParams(formData) // 提交表單數據
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
                if (data.success) {
                    isRegistered = true; // 更新報名狀態
                    refreshEventDetails();  // 刷新活動詳情
                    registrationModal.style.display = 'none'; // 關閉報名視窗
                }
            })
            .catch(err => {
                console.error('報名失敗：', err);
            });
    });

    // 點擊取消報名按鈕
    cancelRegistrationButton.addEventListener('click', function () {
        if (confirm('您確定要取消報名嗎？')) {
            fetch(`/cancel-registration/${eventId}`, { method: 'POST' })
                .then(response => response.text())
                .then(message => {
                    alert(message);
                    isRegistered = false; // 更新報名狀態
                    refreshEventDetails();  // 刷新活動詳情
                })
                .catch(() => {
                    alert('取消報名失敗，請稍後再試。');
                });
        }
    });

    // 頁面加載時再次刷新活動詳情
    refreshEventDetails();
});
