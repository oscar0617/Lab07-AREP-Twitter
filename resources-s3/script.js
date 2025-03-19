let API_GATEWAY_URL = "https://djppvu4h8a.execute-api.us-east-1.amazonaws.com/beta";
let API_POSTS = "https://hi85golm3d.execute-api.us-east-1.amazonaws.com/beta";

document.addEventListener("DOMContentLoaded", function () {
    const loggedUserElement = document.getElementById("logged-user");
    const hiloSelect = document.getElementById("hilo-select");
    const postContent = document.getElementById("post-content");
    const postButton = document.getElementById("post-btn");
    const postsContainer = document.getElementById("posts-container");

    let loggedUser = localStorage.getItem("usuario");

    if (loggedUser && loggedUserElement) {
        loggedUserElement.textContent = localStorage.getItem("usuario");
    } else {
        loggedUserElement.textContent = "pruebas S3";
    }

    function obtenerToken() {
        const urlParams = new URLSearchParams(window.location.search);
        const authCode = urlParams.get("code");

        if (!authCode) {
            window.location.href = "https://64tzjd9mq4.execute-api.us-east-1.amazonaws.com/beta/users"
            return;
        }

        const clientId = "37hclu5v65ut7f6q978aaj10ss";
        const clientSecret = "ube828g3jf9gno52i2fsqjgchspkfesfnharv1c2be3voglg150";
        const redirectUri = "https://minitwitters3.s3.us-east-1.amazonaws.com/index.html";
        const tokenUrl = "https://us-east-11xrvet0n4.auth.us-east-1.amazoncognito.com/oauth2/token";

        const body = new URLSearchParams({
            grant_type: "authorization_code",
            client_id: clientId,
            client_secret: clientSecret,
            redirect_uri: redirectUri,
            code: authCode
        });

        fetch(tokenUrl, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: body.toString(),
        })
            .then(response => response.json())
            .then(data => {

                const username = extraerUsername(data.id_token);
                console.log("🎭 Username extraído:", username);

                localStorage.setItem("jwt", data.id_token);
                localStorage.setItem("usuario", username);

                if (loggedUserElement) {
                    loggedUserElement.textContent = username;
                }
            })
            .catch(error => {
                window.location.href = "https://64tzjd9mq4.execute-api.us-east-1.amazonaws.com/beta/users";
            });
    }

    function extraerUsername(idToken) {
        try {
            const base64Url = idToken.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const payload = JSON.parse(atob(base64));

            return payload["cognito:username"] || "Usuario no encontrado";
        } catch (error) {
            window.location.href = "https://64tzjd9mq4.execute-api.us-east-1.amazonaws.com/beta/users";
            return "Error al obtener username";
        }
    }
    obtenerToken();
    function cargarHilos() {
        fetch(`${API_GATEWAY_URL}/hilos/all`)
            .then(response => response.json())
            .then(data => {
                hiloSelect.innerHTML = "";
                data.forEach(hilo => {
                    let option = document.createElement("option");
                    option.value = hilo.id;
                    option.textContent = hilo.nombre;
                    hiloSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Error al cargar hilos:", error);
                hiloSelect.innerHTML = "<option value='' disabled>Error al cargar</option>";
            });
    }
    function cargarHilos() {
        fetch(`${API_GATEWAY_URL}/hilos/all`)
            .then(response => response.json())
            .then(data => {
                hiloSelect.innerHTML = "";
                data.forEach(hilo => {
                    let option = document.createElement("option");
                    option.value = hilo.id;
                    option.textContent = hilo.nombre;
                    hiloSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Error al cargar hilos:", error);
                hiloSelect.innerHTML = "<option value='' disabled>Error al cargar</option>";
            });
    }
    function cargarPosts() {
        postsContainer.innerHTML = "<p>Cargando posts...</p>";

        fetch(`${API_POSTS}/posts/all`)
            .then(response => response.json())
            .then(posts => {
                postsContainer.innerHTML = "";
                const hilosMap = {};

                posts.forEach(post => {
                    if (!hilosMap[post.nombreHilo]) {
                        hilosMap[post.nombreHilo] = [];
                    }
                    hilosMap[post.nombreHilo].push(post);
                });

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
    postButton.addEventListener("click", function () {
        const selectedHiloId = hiloSelect.value;
        const content = postContent.value.trim();

        if (!selectedHiloId) {
            alert("Selecciona un hilo.");
            return;
        }
        if (content.length === 0 || content.length > 140) {
            alert("El contenido debe tener entre 1 y 140 caracteres.");
            return;
        }
        const selectedHiloNombre = hiloSelect.options[hiloSelect.selectedIndex].text;

        console.log("User "+ localStorage.getItem("usuario"));
        fetch(`${API_POSTS}/posts/create`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                username: localStorage.getItem("usuario"),
                nombreHilo: selectedHiloNombre,
                content: content
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error en la respuesta del servidor");
                }
                return response.json();
            })
            .then(() => {
                alert("Post agregado correctamente.");
                postContent.value = "";
                cargarPosts(); 
            })
            .catch(error => {
                console.error("Error al enviar post:", error);
                alert("Hubo un error al postear.");
            });
    });

    cargarHilos();
    cargarPosts();
});
