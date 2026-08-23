let stompClient = null;
let currentConversationId = null;

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        const convIdElement = document.getElementById('messages');
        if (convIdElement && convIdElement.dataset.currentConvId) {
            currentConversationId = convIdElement.dataset.currentConvId;
            selectConversation(currentConversationId);
        }
    });
}

function selectConversation(convId) {
    currentConversationId = convId;
    document.getElementById('conversationId').value = convId;

    document.querySelectorAll('.conv-item').forEach(el => el.classList.remove('active'));
    const activeItem = document.querySelector(`.conv-item[data-conv-id="${convId}"]`);
    if (activeItem) activeItem.classList.add('active');

    fetch('/api/chat/' + convId)
        .then(response => response.json())
        .then(messages => {
            const messagesDiv = document.getElementById('messages');
            messagesDiv.innerHTML = '';
            messages.forEach(msg => appendMessage(msg));
        });

    if (stompClient) {
        stompClient.subscribe('/topic/conversation/' + convId, function (message) {
            appendMessage(JSON.parse(message.body));
        });
    }
}

function appendMessage(msg) {
    const messagesDiv = document.getElementById('messages');
    const div = document.createElement('div');
    div.className = 'message';
    div.innerHTML = `<strong>${msg.sender.username}</strong>: ${msg.content}`;
    messagesDiv.appendChild(div);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}

document.addEventListener('DOMContentLoaded', function () {
    connect();

    document.querySelectorAll('.conv-item').forEach(item => {
        item.addEventListener('click', function () {
            selectConversation(this.dataset.convId);
        });
    });

    document.getElementById('messageForm').addEventListener('submit', function (e) {
        e.preventDefault();
        const content = document.getElementById('messageContent').value;
        const convId = document.getElementById('conversationId').value;
        if (content && stompClient && convId) {
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({
                conversationId: convId,
                content: content
            }));
            document.getElementById('messageContent').value = '';
        }
    });
});