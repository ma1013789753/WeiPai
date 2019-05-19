import Cookies from 'js-cookie'

const TokenKey = 'authorization'
const oauthKey = 'oauth'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getOauth() {
  return Cookies.get(oauthKey)
}

export function setOauth(oauth) {
  return Cookies.set(oauthKey, oauth)
}

export function removeOauth() {
  return Cookies.remove(oauthKey)
}
