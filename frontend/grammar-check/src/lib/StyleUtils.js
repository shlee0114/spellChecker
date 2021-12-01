import { css } from 'styled-components'
    
// 미디어 쿼리 헬퍼: https://www.styled-components.com/docs/advanced#media-templates 참조
export const sizes = {
    wide : '1200px',
    desktop : '992px',
    tablet : '768px',
    phone : '376px'
}

export const media = Object.keys(sizes).reduce((acc, label) => {
    acc[label] = (...args) => css`
        @media (max-width : ${sizes[label]}){
            ${css(...args)}
        }   
    `
    return acc
}, {})

// 그림자 효과: https://codepen.io/sdthornton/pen/wBZdXq 기반
export const shadow = (weight) =>{
    const shadow =[
        css `box-shadow: 0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23);`,
        css `box-shadow: 0 14px 28px rgba(0,0,0), 0 10px 10px rgba(0,0,0,0.8);`
    ]

    return shadow[weight]
}