let stompClient = null;
let currentConversationId = null;

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        // Subscribe to all conversations? For simplicity, we'll subscribe to a specific conversation when selected.
    });
}

function selectConversation(convId) {
    currentConversationId = convId;
    // Load previous messages via REST
    fetch(`/chat/${convId}`)
        .then(response => response.json())
        .then(messages => {
            const messagesDiv = document.getElementById('messages');
            messagesDiv.innerHTML = '';
            messages.forEach(msg => {
                appendMessage(msg);
            });
            // Subscribe to this conversation's topic
            if (stompClient) {
                stompClient.subscribe(`/topic/conversation/${convId}`, function(message) {
                    appendMessage(JSON.parse(message.body));
                });
            }
        });
}

function appendMessage(msg) {
    const messagesDiv = document.getElementById('messages');
    const div = document.createElement('div');
    div.className = 'message';
    div.innerHTML = `<strong>${msg.sender.username}</strong>: ${msg.content}`;
    messagesDiv.appendChild(div);
}

document.addEventListener('DOMContentLoaded', function() {
    connect();

    document.querySelectorAll('.conv-item').forEach(item => {
        item.addEventListener('click', function() {
            document.querySelectorAll('.conv-item').forEach(el => el.classList.remove('active'));
            this.classList.add('active');
            const convId = this.dataset.convId;
            document.getElementById('conversationId').value = convId;
            selectConversation(convId);
        });
    });

    document.getElementById('messageForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const content = document.getElementById('messageContent').value;
        const convId = document.getElementById('conversationId').value;
        if (content && stompClient && convId) {
            stompClient.send('/app/chat.sendMessage', {}, JSON.stringify({
                conversationId: convId,
                content: content
            }));
            document.getElementById('messageContent').value = '';
        }
    });
});