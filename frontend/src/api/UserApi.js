import { instance } from '@/api/index'

const Login = (payload, callback, errorCallback) => {
    instance
        .post('/member', payload)
        .then(res => callback(res))
        .catch(err => errorCallback(err))
}

const UserApi = {
    Login: (data, callback, errorCallback) =>
        Login(data, callback, errorCallback),
}

export default UserApi
