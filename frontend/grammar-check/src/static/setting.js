import dotenv from 'dotenv'
dotenv.config()

export const serverIp = `${process.env.REACT_APP_SERVER_IP}/api/`
export const graphqlServerIp = `${process.env.REACT_APP_SERVER_IP}/graphql`