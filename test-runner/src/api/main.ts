import Koa from 'koa'
import KoaCors from '@koa/cors'
import KoaBodyParser from 'koa-bodyparser'

import router from '@api/router'
import environment from '@api/utils/environment'

const {port, host} = environment

const server = new Koa()
  .use(KoaBodyParser({
    enableTypes: ["text"]
  }))
  .use(KoaCors())
  .use(router.routes())
  .use(router.allowedMethods())
  .listen(port, host)

server.on('listening', () => {
  const serverAddress = getServerAddress()

  console.log(`The mock-api is listening on the following address: ${serverAddress}`)
  console.log('\nThe following end-points are available:')

  router.stack.forEach(({ methods, path }) =>
    console.log(`- ${[serverAddress, path].join('')} ${methods.map(method => `[${method}]`).join('')}`)
  )
})

server.on('error', error => console.log(error))

function getServerAddress() {
  const serverInformation = server.address() || { address: 'localhost', port }

  if (typeof serverInformation == 'string') {
    return serverInformation
  } else {
    let { address } = serverInformation
    const { port: serverPort } = serverInformation

    console.log(address)

    if (address === '::1') {
      address = 'http://localhost'
    }

    return [address, serverPort].join(':')
  }
}
