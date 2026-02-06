const statusEls = {
  register: document.querySelector('[data-status="register"]'),
  login: document.querySelector('[data-status="login"]'),
  upload: document.querySelector('[data-status="upload"]'),
};

const setStatus = (key, message, isError = false) => {
  const el = statusEls[key];
  if (!el) return;
  el.textContent = message;
  el.classList.toggle('error', isError);
  el.classList.toggle('success', !isError);
};

const handleJson = async (response) => {
  const text = await response.text();
  if (!response.ok) {
    throw new Error(text || 'Request failed');
  }
  return text || 'Success';
};

document.getElementById('registerForm').addEventListener('submit', async (event) => {
  event.preventDefault();
  setStatus('register', 'Submitting...');

  const form = event.currentTarget;
  const payload = {
    firstName: form.firstName.value.trim(),
    lastName: form.lastName.value.trim(),
    email: form.email.value.trim(),
    password: form.password.value,
  };

  try {
    const response = await fetch('/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });
    const message = await handleJson(response);
    setStatus('register', message, false);
    form.reset();
  } catch (error) {
    setStatus('register', error.message, true);
  }
});

document.getElementById('loginForm').addEventListener('submit', async (event) => {
  event.preventDefault();
  setStatus('login', 'Submitting...');

  const form = event.currentTarget;
  const payload = {
    email: form.email.value.trim(),
    password: form.password.value,
  };

  try {
    const response = await fetch('/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });
    const message = await handleJson(response);
    setStatus('login', message, false);
    form.reset();
  } catch (error) {
    setStatus('login', error.message, true);
  }
});

document.getElementById('uploadForm').addEventListener('submit', async (event) => {
  event.preventDefault();
  setStatus('upload', 'Uploading...');

  const form = event.currentTarget;
  const formData = new FormData();
  formData.append('userId', form.userId.value);
  formData.append('file', form.file.files[0]);

  try {
    const response = await fetch('/resume', {
      method: 'POST',
      body: formData,
    });
    const message = await handleJson(response);
    setStatus('upload', message, false);
    form.reset();
  } catch (error) {
    setStatus('upload', error.message, true);
  }
});
