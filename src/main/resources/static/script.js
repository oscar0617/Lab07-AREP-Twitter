let IPADDRESS = "localhost";


document.addEventListener("DOMContentLoaded", function () {
    const loggedUserElement = document.getElementById("logged-user");
    const hiloSelect = document.getElementById("hilo-select");
    const postContent = document.getElementById("post-content");
    const postButton = document.getElementById("post-btn");
    const postsContainer = document.getElementById("posts-container");

    let loggedUser = localStorage.getItem("usuario");

    if (loggedUser && loggedUserElement) {
        loggedUserElement.textContent = loggedUser;
    }

//Seguridad y login
    window.addEventListener('load', function () {
        const currentPage = window.location.pathname;
    
        if (!localStorage.getItem('loggedIn') && 
            !currentPage.includes('login.html') && 
            !currentPage.includes('registro.html')) {
            window.location.href = 'login.html';
        }
    });

    if (document.getElementById('loginForm')) {
        const loginForm = document.getElementById('loginForm');
        loginForm.addEventListener('submit', function (event) {
            event.preventDefault();
            const username = document.getElementById('loginUsername').value;
            const password = document.getElementById('loginPassword').value;

            fetch(`http://${IPADDRESS}:8080/users/authenticate`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password }),
                credentials: 'include' 
            })
            .then(response => {
                if (response.ok) {
                    localStorage.setItem('loggedIn', 'true');
                    alert('Inicio de sesión exitoso');
                    window.location.href = 'index.html';
                    localStorage.setItem('usuario', username)
                } else {
                    alert('Credenciales inválidas');
                }
            })
            .catch(error => console.error('Error:', error));
        });
    }

    if (document.getElementById('registerForm')) {
        const registerForm = document.getElementById('registerForm');
        registerForm.addEventListener('submit', function (event) {
            event.preventDefault();
            const username = document.getElementById('registerUsername').value;
            const password = document.getElementById('registerPassword').value;

            fetch(`http://${IPADDRESS}:8080/users/register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password }),
                credentials: 'include' 
            })
            .then(response => {
                if (response.ok) {
                    alert('Usuario registrado exitosamente');
                    window.location.href = 'login.html';
                } else {
                    alert('Error al registrar el usuario');
                }
            })
            .catch(error => console.error('Error:', error));
        });
    }

    
    // 🔹 Obtener la lista de hilos
    function cargarHilos() {
        fetch(`http://${IPADDRESS}:8080/hilos/all`)
            .then(response => {
                if (!response.ok) throw new Error("Error en la respuesta del servidor");
                return response.json();
            })
            .then(data => {
                hiloSelect.innerHTML = ""; // Limpiar opciones previas
                data.forEach(hilo => {
                    let option = document.createElement("option");
                    option.value = hilo.nombre; // Usar nombre en lugar de ID
                    option.textContent = hilo.nombre; // Mostrar nombre
                    hiloSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Error al cargar hilos:", error);
                hiloSelect.innerHTML = "<option value='' disabled>Error al cargar</option>";
            });
    }

    // 🔹 Obtener posts de los hilos
    function cargarPosts() {
        postsContainer.innerHTML = "<p>Cargando posts...</p>";

        fetch(`http://${IPADDRESS}:8080/posts`)
            .then(response => {
                if (!response.ok) throw new Error("Error en la respuesta del servidor");
                return response.json();
            })
            .then(posts => {
                postsContainer.innerHTML = ""; // Limpiar antes de agregar nuevos
                const hilosMap = {};

                // Agrupar posts por nombre de hilo
                posts.forEach(post => {
                    if (!hilosMap[post.nombreHilo]) {
                        hilosMap[post.nombreHilo] = [];
                    }
                    hilosMap[post.nombreHilo].push(post);
                });

                // Crear la estructura HTML
                Object.keys(hilosMap).forEach(hiloNombre => {
                    const hiloDiv = document.createElement("div");
                    hiloDiv.classList.add("hilo");
                    hiloDiv.innerHTML = `<h3>${hiloNombre}</h3>`;

                    hilosMap[hiloNombre].forEach(post => {
                        const postDiv = document.createElement("div");
                        postDiv.classList.add("post");
                        postDiv.innerHTML = `<p><strong>${post.username}:</strong> ${post.content}</p>`;
                        hiloDiv.appendChild(postDiv);
                    });

                    postsContainer.appendChild(hiloDiv);
                });
            })
            .catch(error => {
                console.error("Error al cargar posts:", error);
                postsContainer.innerHTML = "<p>Error al cargar posts.</p>";
            });
    }

    // 🔹 Crear un nuevo post
    postButton.addEventListener("click", function () {
        const selectedHilo = hiloSelect.value;
        const content = postContent.value.trim();

        if (!selectedHilo) {
            alert("Selecciona un hilo.");
            return;
        }
        if (content.length === 0 || content.length > 140) {
            alert("El contenido debe tener entre 1 y 140 caracteres.");
            return;
        }

        fetch(`http://${IPADDRESS}:8080/posts`, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `username=${loggedUser}&nombreHilo=${encodeURIComponent(selectedHilo)}&content=${encodeURIComponent(content)}`
        })
            .then(response => {
                if (!response.ok) throw new Error("Error en la respuesta del servidor");
                return response.json();
            })
            .then(() => {
                alert("Post agregado correctamente.");
                postContent.value = ""; // Limpiar textarea
                cargarPosts(); // Recargar los posts
            })
            .catch(error => {
                console.error("Error al enviar post:", error);
                alert("Hubo un error al postear.");
            });
    });

    // Cargar hilos y posts al iniciar
    cargarHilos();
    cargarPosts();
});
